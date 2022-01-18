package com.cloud.example.jpa.client;

import com.cloud.example.base.BusinessCode;
import com.cloud.example.base.ResultResponse;
import com.cloud.example.jpa.aggservice.impl.OrderAggServiceImpl;
import com.cloud.example.jpa.comm.RLockUtil;
import com.cloud.example.jpa.jpa.dao.OrderRepository;
import com.cloud.example.jpa.jpa.dao.ResourceRepository;
import com.cloud.example.jpa.jpa.dao.UserRepository;
import com.cloud.example.jpa.jpa.domain.ResourceDAO;
import com.cloud.example.jpa.jpa.domain.UserDAO;
import com.cloud.example.jpa.param.req.OrderCreateReq;
import com.cloud.example.jpa.param.rsp.OrderCreateRsp;
import com.cloud.example.jpa.service.impl.OrderServiceImpl;
import com.cloud.example.jpa.service.impl.ResourceServiceImpl;
import com.cloud.example.jpa.service.impl.UserServiceImpl;
import com.cloud.example.jpa.strategy.UserCheckContext;
import com.cloud.example.jpa.strategy.UserCheckStrategy;
import com.cloud.example.jpa.strategy.one.UserCheckOneStrategy;
import com.cloud.example.jpa.strategy.tow.UserCheckTowStrategy;
import com.cloud.example.utils.SnowflakeIdWorker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Spy和@Mock的区别：
 * @Spy修饰的属性里面的方法可以按照真实情况执行,在需要的时候可以打桩模拟执行结果,使用方式是Mockito.doReturn().when()--全都执行,有需要在改。
 * @Mock修饰的属性都是null,在执行单元测试的时候每个方法都需要打桩模拟执行结果,使用方式是Mockito.when().thenReturn()--全部不执行,避免意外。 <p>
 * <p>
 * <p>
 * @Spy和@InjectMocks 的应用场景：
 * @Spy修饰的属性在通过Mockito打桩数据时,无法将要打桩的属性自动注入。
 * @InjectMocks则可以自动注入,另外@InjectMocks修饰的必须是完整的类。 例如：
 * UserServiceImpl属性如果用@Spy修饰,则在使用Mockito打桩模拟数据时,在UserServiceImpl中的userRepository属性直接就是null。
 * UserServiceImpl属性如果用@InjectMocks修饰,则在使用Mockito打桩模拟数据时,在UserServiceImpl中的userRepository属性自动注入的是mock生成的代理对象。<p>
 * <p>
 * <p>
 * @Spy、@Mock和@InjectMocks修饰属性类型： <p>
 * 抽象类需要特殊处理(userCheckStrategy = Mockito.mock ( UserCheckStrategy.class, Answers.CALLS_REAL_METHODS);)。
 * @Spy：可以自动注入到其他实现类的属性中。修饰接口不会报错,不过因为接口没有实现逻辑,所以不打桩模拟的时候,接口方法永远返回null。
 * @Mock：可以自动注入到其他实现类的属性中。用@Mock修饰的属性不做打桩模拟返回结果的情况,返回的都是null，所以无所谓修饰的属性是不是接口。
 * @InjectMocks：不可以自动注入到其他实现类的属性中。修饰接口会报错。 <p>
 * <p>
 * <p>
 * 使用建议：
 * 1、默认使用@Spy,有需要打桩模拟返回结果的情况可以自定义模拟返回结果,尽可能的覆盖更多的代码逻辑。
 * 2、对依赖项全部使用@Mock,每个依赖项都打桩模拟返回结果,有遗漏的地方会报空指针异常,在测试用例的开发阶段就能识别出来,尽可能的减少依赖项对单元测试执行结果的影响。
 * 3、对于实现类中包含需要通过Mockito打桩模拟结果的依赖项属性时,使用@InjectMocks,使被@Mock修饰的属性能够自动注入到实现类中。
 * 4、@Spy修饰实现类、@InjectMocks修饰实现类、@Mock修饰接口。
 * <p>
 * <p>
 * 疑问：
 * Q：在OrderAggServiceImpl中的orderService属性如果没有手动set方式赋值的时候,orderService属性是null。
 * 而其他属性threadPoolTaskExecutor、resourceService、snowflakeIdWorker都是自动有值不为空的。区别是orderService是@InjectMocks修饰,
 * 而其他有值的属性是@Spy修饰,但是将orderService属性用@Spy和@InjectMocks同时修饰也不能自动赋值,另外这两个注解同时使用时只有离属性最近的一个注解起作用。
 * A：
 * <p>
 * Q：@Spy、@Mock和@InjectMocks三个注解的注入规则是什么？什么时候能自动注入,什么时候需要手动赋值?
 * A：
 * <p>
 * https://blog.csdn.net/qq_27376871/article/details/122512721
 * <p>
 * MockitoAnnotations.initMocks(this); 的一个替代方案是使用 @RunWith(MockitoJUnitRunner.class) 。
 */
class OrderClientTest {


    @Spy
    OrderClient orderClient;
    @InjectMocks
    RLockUtil rLockUtil;
    @Spy
    AsyncTaskExecutor asyncExecutor;
    @Spy
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Spy
    SnowflakeIdWorker snowflakeIdWorker;

    @Spy
    UserCheckContext userCheckContext;
    //    @InjectMocks
//    UserCheckStrategy userCheckStrategy;
    @Spy
    UserCheckOneStrategy userCheckOneStrategy;
    @Spy
    UserCheckTowStrategy userCheckTowStrategy;
    @InjectMocks
    UserServiceImpl userService;

    @InjectMocks
    OrderServiceImpl orderService;
    @InjectMocks
    ResourceServiceImpl resourceService;
    @InjectMocks
    OrderAggServiceImpl orderAggService;

    @Mock
    RedissonClient redisson;
    @Mock
    UserRepository userRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ResourceRepository resourceRepository;


    private final String USER_ID = "2345323";
    private final String RESOURCE_ID = "8234455323";

    @BeforeEach
    void setup() {
//        //@InjectMocks修饰抽象类时的特殊处理方式
//        userCheckStrategy = Mockito.mock(UserCheckStrategy.class, Answers.CALLS_REAL_METHODS);
        MockitoAnnotations.initMocks(this);

        orderAggService.setOrderService(orderService);
        orderAggService.setResourceService(resourceService);

        Map<String, UserCheckStrategy> aggStrategyMap = new ConcurrentHashMap<>(8);
        aggStrategyMap.put("ONE_STRATEGY", userCheckOneStrategy);
        aggStrategyMap.put("TOW_STRATEGY", userCheckTowStrategy);
        userCheckContext.setStrategyMap(aggStrategyMap);
        userCheckOneStrategy.setUserService(userService);
        userCheckOneStrategy.setResourceService(resourceService);
        userCheckOneStrategy.setThreadPoolTaskExecutor(threadPoolTaskExecutor);

        orderClient.setRLockUtil(rLockUtil);
        orderClient.setOrderService(orderService);
        orderClient.setOrderAggService(orderAggService);
        orderClient.setUserCheckContext(userCheckContext);
    }

    @SneakyThrows
    @Test
    void save() {
        // 新增对异步线程里面Runnable方法的驱动
        Mockito.doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(threadPoolTaskExecutor).execute(Mockito.any(Runnable.class));
        // 新增对异步线程里面Runnable方法的驱动
        Mockito.doAnswer(
                (InvocationOnMock invocation) -> {
                    ((Runnable) invocation.getArguments()[0]).run();
                    return null;
                }
        ).when(asyncExecutor).execute(Mockito.any(Runnable.class));
        //分布式锁
        RLock rLock = Mockito.mock(RLock.class);
        Mockito.when(redisson.getLock(Mockito.anyString()))
                .thenReturn(rLock);
        Mockito.when(rLock.tryLock(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(TimeUnit.class)))
                .thenReturn(true);
        Mockito.doReturn("923486756453").when(snowflakeIdWorker).nextId();

        UserDAO userDAO = new UserDAO();
        userDAO.setId(USER_ID);
        userDAO.setNickName("那个啥");
        userDAO.setPassword("没那个啥");
        userDAO.setPhoneNo("18518111111");
        userDAO.setAvatarUrl("https://baidu.com/avatarUrl.jpg");
        Mockito.when(userRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(userDAO));
        Mockito.when(orderRepository.findByUserId(Mockito.anyString()))
                .thenReturn(Optional.empty());
        ResourceDAO resourceDAO = new ResourceDAO();
        resourceDAO.setId(RESOURCE_ID);
        resourceDAO.setCategoryId(978675);
        resourceDAO.setResourceLogo("https://baidu.com/resourceLogo.jpg");
        resourceDAO.setResourceName("那个呀");
        resourceDAO.setResourceStatus(0);
        Mockito.when(resourceRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(resourceDAO));


        OrderCreateReq orderCreateReq = new OrderCreateReq();
        orderCreateReq.setSource("ONE_STRATEGY");
        orderCreateReq.setUserId(USER_ID);
        orderCreateReq.setResourceId(RESOURCE_ID);
        orderCreateReq.setQuantity(1);
        ResultResponse<OrderCreateRsp> resultResponse = orderClient.save(orderCreateReq);
        assertThat(resultResponse.getStatusCode(), is(BusinessCode.SUCCESS.getCode()));
    }
}