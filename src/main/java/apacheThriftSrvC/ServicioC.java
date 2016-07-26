package apacheThriftSrvC;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ServicioC {

	Map<String, Map<String, Map<String,String>>> stock;

	public ServicioC() {
		stock = StockDao.instance.getStock();
	}
	
	
	@RequestMapping(value = "/servicioC", method = RequestMethod.POST)
	public @ResponseBody RespuestaNoThriftStock servicioC(@RequestBody RequestMessageStock requestMessageStock) {
		
		// Vars
		long iniTime = System.currentTimeMillis();
		RespuestaNoThriftStock respuestaNoThriftStock = new RespuestaNoThriftStock();
		respuestaNoThriftStock.setStock("0");

		// Obtain stock for prenda (name, color & size)
		Map<String, Map<String,String>> stockPrenda = stock.get(requestMessageStock.getNombre());
		if(stockPrenda!=null){
			Map<String, String> stockColor = stockPrenda.get(requestMessageStock.getColor());
			if(stockColor!=null){
				String cantidad = stockColor.get(requestMessageStock.getTalla());
				if(cantidad!=null){
					respuestaNoThriftStock.setStock(cantidad);
				}
			}
		}
		
		System.out.println("FIN ServicioC.  ts = {" + (System.currentTimeMillis() - iniTime) + "}");
		
		return respuestaNoThriftStock;
	}
	
	/*******************************************
	 * MAIN                                    *
	 *                                         *
	 * @param args                             *
	 *                                         *
	 * @throws Exception                       *
	 *                                         *
	 ******************************************/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServicioC.class, args);
	}
}
