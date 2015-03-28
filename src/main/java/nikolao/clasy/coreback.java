/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nikolao.clasy;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import us.jubat.classifier.ClassifierClient;
import us.jubat.classifier.EstimateResult;
import us.jubat.common.Datum;
import static nikolao.clasy.util.*;

/**
 *
 * @author nikolao {Ioannis Nikolaou AM 4504 mailto:Gianhspc@gmail.com}
 * 9/3/2015 Created{First write}
 */

public class coreback {
    private final long id;
    private final List<List<EstimateResult>> results;
    public coreback(long id,List<List<EstimateResult>> Score) throws UnknownHostException {
        this.id = id;
        this.results=Score;
    }
    public long getId() {
        return id;
    }
 
    public List<List<EstimateResult>> getResults() {
        return results;
    }
}
