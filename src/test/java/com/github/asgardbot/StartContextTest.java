package com.github.asgardbot;

import com.github.asgardbot.shell.TestShellRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TestShellRunner.class)
public class StartContextTest {

    @Test
    public void contextLoads() {}
}

