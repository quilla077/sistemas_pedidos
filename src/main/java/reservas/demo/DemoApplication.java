package reservas.demo;


import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import reservas.demo.services.TareaAutomatica;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@EnableScheduling
@SpringBootApplication
@AllArgsConstructor
public class DemoApplication {
	//public static final String ACCOUNT_SID = "AC757a72ed75d2d5251925c7963bbea74b";
	//public static final String AUTH_TOKEN = "65251512c9340d9ce93f9849ba5f283a";
	//public static final TareaAutomatica tareaAutomatica = null;

	public static void main(String[] args) throws UnknownHostException, SocketException {
		SpringApplication.run(DemoApplication.class, args);
		InetAddress localHost = InetAddress.getLocalHost();
		System.out.println("Direccion IP: "+localHost.getHostAddress());

//		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//		TareaAutomatica miServicio = context.getBean(TareaAutomatica.class);
//		miServicio.revisarProductosCaducados();

		System.out.println("Cantos de liberacion, Gloria a Dios!!!!");
	}



}
