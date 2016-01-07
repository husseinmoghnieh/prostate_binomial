import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.BinomialModelPrediction;

/**
 * Created by huss on 16-01-06.
 */
public class PredictMain {
    static EasyPredictModelWrapper prostateModel;

    public static void main(String[] args) {
        System.out.println("prediction...");
        ProstateModel rawProstateModel = new ProstateModel();
        prostateModel = new EasyPredictModelWrapper(rawProstateModel);

//        ID,CAPSULE,AGE,RACE,DPROS,DCAPS,PSA,VOL,GLEASON
//        1,0,65,1,2,1,1.4,0,6
        RowData test1 = new RowData();
        test1.put("AGE", "65");
        test1.put("RACE", "1");
        test1.put("DPROS", "2");
        test1.put("DCAPS", "1");
        test1.put("PSA", "1.4");
        test1.put("VOL", "0");
        test1.put("GLEASON", "6");

        RowData test2 = new RowData();
        test2.put("AGE", "71");
        test2.put("RACE", "1");
        test2.put("DPROS", "3");
        test2.put("DCAPS", "2");
        test2.put("PSA", "3.3");
        test2.put("VOL", "0");
        test2.put("GLEASON", "8");


        try {
            BinomialModelPrediction p = predictProstate(test1);

            System.out.println("prediction: " +  p.labelIndex + "\t" +  p.classProbabilities[0] + "\t" + p.classProbabilities[1]);


            p = predictProstate(test2);
            System.out.println("prediction: " +  p.labelIndex + "\t" +  p.classProbabilities[0] + "\t" + p.classProbabilities[1]);



        } catch (PredictException p) {

        }
    }


    private static BinomialModelPrediction predictProstate(RowData row) throws PredictException {
        return prostateModel.predictBinomial(row);
    }
}