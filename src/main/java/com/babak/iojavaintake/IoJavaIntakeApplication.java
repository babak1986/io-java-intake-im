package com.babak.iojavaintake;

import com.babak.iojavaintake.excption.UsernameExistsException;
import com.babak.iojavaintake.tedtalk.TedTalkDto;
import com.babak.iojavaintake.tedtalk.TedTalkService;
import com.babak.iojavaintake.user.UserService;
import com.babak.iojavaintake.userauthority.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
public class IoJavaIntakeApplication implements ApplicationRunner {

    private final UserService userService;
    private final TedTalkService tedTalkService;
    private final Logger logger = LoggerFactory.getLogger(IoJavaIntakeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IoJavaIntakeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userService.create("test", "test", Role.USER);
        } catch (UsernameExistsException e) {
            logger.info("User 'test' exists.");
        }

        logger.info("Initializing database...");
        long count = tedTalkService.count();
        if (count == 0) {
            List<TedTalkDto> tedTalks = tedTalkService.loadCsvIntoEntityList(TedTalkDto.class, IoJavaIntakeApplication.class.getResourceAsStream("/data.csv"));
            if (tedTalks.size() != 0) {
                tedTalks.forEach(tedTalkService::create);
                logger.info("Database is initialized with {} records.", tedTalks.size());
            } else {
                logger.error("Database cannot be initialized. No record is retrieved from data.csv file.");
            }
        } else {
            logger.info("Initializing database done ({} TedTalks).", count);
        }
    }

}
