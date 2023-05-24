package com.example.basedomains;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Soberin *guiño guiño*
 * Esta parte del proyecto, está hecho como una parte en común para cada uno de los servicios, dado que si se crea un evento, este objeto va a ser creado por
 * el productor pero lo leeará el consumidor, entonces es como una librería en común que importas al resto de microservicios como dependencia, revisa el pom
 * de Order y de Stock
 */
@SpringBootTest
class BaseDomainsApplicationTests {

	@Test
	void contextLoads() {
	}

}
