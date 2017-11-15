package cn.itsource.aigou.job.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itsource.aigou.job.QuartzUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-service-quartz.xml")
public class JobTest {
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	@Test
	public void testName() throws Exception {
		//schedulerFactory通过spring注入
		Scheduler sche = schedulerFactory.getScheduler();
		String job_name = "JOB_NAME";
		String clazz = "cn.itsource.aigou.job.MainJob";
		String cron = "/1 * * * * ? ";
		Map<String, Object> params = new HashMap<>();
		params.put("firstJob", 1);
		params.put("firstName", "myFirstName");
		QuartzUtils.addJob(sche, job_name, Class.forName(clazz), params, cron);

		Thread.sleep(10000);
		System.out.println("【移除定时】开始...");
		QuartzUtils.removeJob(sche, job_name);
		System.out.println("【移除定时】成功");
	}
}
