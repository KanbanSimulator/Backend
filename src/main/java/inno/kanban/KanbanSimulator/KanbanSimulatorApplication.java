package inno.kanban.KanbanSimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"inno.kanban.KanbanSimulator"})
public class KanbanSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanSimulatorApplication.class, args);
	}

}
