package ar.com.nec.pasantia.blockbuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ar.com.nec.pasantia.blockbuster.repository")
@EntityScan("ar.com.nec.pasantia.blockbuster.entities")
@ComponentScan({"ar.com.nec.pasantia.blockbuster","ar.com.nec.pasantia.blockbuster.controller"})
@SpringBootApplication
public class BlockbusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockbusterApplication.class, args);
    }

}

