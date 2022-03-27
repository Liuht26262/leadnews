package login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tanran.model.mappers.app")
public class LoginJarApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginJarApplication.class,args);
    }
}
