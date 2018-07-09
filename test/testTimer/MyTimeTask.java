package testTimer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTimeTask extends TimerTask {

	@Override
	public void run() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("do timerTask..."+ simpleDateFormat.format(new Date()));
	}

}
