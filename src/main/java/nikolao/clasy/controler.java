/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nikolao.clasy;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import static nikolao.clasy.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import us.jubat.classifier.EstimateResult;

/**
 *
 * @author nikolao {Ioannis Nikolaou AM 4504 mailto:Gianhspc@gmail.com}
 * 9/3/2015 Created{First write}
 */

@RestController
public class controler {
    private final AtomicLong counter = new AtomicLong();
    private List<List<EstimateResult>> results;
    @RequestMapping("/clasify")
    public coreback clasify(@RequestParam(value="value", defaultValue="0") double value) throws UnknownHostException {
        results=clasfy(value);
        return new coreback(counter.incrementAndGet(),results);
        //return results;
    }
 
    
}