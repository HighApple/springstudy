package com.gdu.prj01.xml03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // bean을 정의하고 사용하기 위해서 쓰는 annotation
public class AppConfig {
  
  @Bean
  public MyConnection myConnection() {
    MyConnection myConnection = new MyConnection();
    myConnection.setDriver("oracle.jdbc.OracleDriver");
    myConnection.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
    myConnection.setUser("GD");
    myConnection.setPassword("1111");
    return myConnection;
  }
  
  @Bean
  public MyDao myDao() {
    MyDao myDao = new MyDao();
    myDao.setMyConnection(myConnection());
    return myDao;
  }
  
  @Bean
  public MyService myService() {
    MyService myService = new MyService();
    myService.setMyDao(myDao());
    return myService;
  }
  
  @Bean
  public MyController myController() {
    MyController myController = new MyController();
    myController.setMyService(myService());
    return myController;
  }

}
