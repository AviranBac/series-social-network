package mahat.aviran.tvseriesfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "mahat.aviran.tvseriesfetcher.entities", "mahat.aviran.common.entities" })
@EnableJpaRepositories(basePackages = { "mahat.aviran.common.repositories" })
public class TvSeriesFetcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(TvSeriesFetcherApplication.class, args);
    }
}
