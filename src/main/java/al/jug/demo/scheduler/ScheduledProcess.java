package al.jug.demo.scheduler;

import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;

@Component
@Slf4j
public class ScheduledProcess {

	@Scheduled(cron = "0 * * * * * ")
	@SchedulerLock(name = "DemoJUG_scheduledProcess")
	public void updateAuctionStatus() {
		try {
			LocalDateTime startTime = LocalDateTime.now();

			log.info("Started automatic process. Start = {} ", startTime);
			
		} catch (Exception e) {
			log.error("Exception while excuting the automatic process", e);
		}
	}
}
