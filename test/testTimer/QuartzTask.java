package testTimer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzTask {
	public void doSimpleTriggerTask() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("do doSimpleTrigger task..."+ simpleDateFormat.format(new Date()));
	}
	public void doCronTriggerTask() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("do doCronTrigger task..."+ simpleDateFormat.format(new Date()));
	}
}
