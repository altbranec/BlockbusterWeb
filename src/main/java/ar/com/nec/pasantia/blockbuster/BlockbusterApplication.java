package ar.com.nec.pasantia.blockbuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"ar.com.nec.pasantia.blockbuster","ar.com.nec.pasantia.blockbuster.controller"})
@SpringBootApplication
public class BlockbusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockbusterApplication.class, args);
    }

}

