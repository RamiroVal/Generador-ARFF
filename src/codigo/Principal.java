package codigo;

import java.util.ArrayList;
import java.util.Collections;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class Principal {

	private ArrayList<String> eqNacional;
	private ArrayList<String> eqAmericana;
	private ArrayList<Attribute> atributos;
	private ArrayList<String> ligas;
	private ArrayList<String> equipos;
	private ArrayList<String> ganadores;
	private Instances datos;

	public Principal(ArrayList<String> eqNacional, ArrayList<String> eqAmericana) {
		this.eqNacional = eqNacional;
		this.eqAmericana = eqAmericana;

		equipos = new ArrayList<>();
		equipos.addAll(eqNacional);
		equipos.addAll(eqAmericana);

		ganadores = new ArrayList<>();
		ganadores.addAll(eqNacional);
		ganadores.addAll(eqAmericana);
		ganadores.add("Empate");
		
		ligas = new ArrayList<>();
		ligas.add("Nacional");
		ligas.add("Americana");
		
		creaAtributos();
		generaDatos();

	}

	private int calculaCarreras() {
		double aux = Math.random();
		if(aux < 0.09){
			return 0;
		}else if(aux < 0.18) {
			return 1;
		}else if(aux < 0.27) {
			return 2;
		}else if(aux < 0.36) {
			return 3;
		}else if(aux < 0.45) {
			return 4;
		}else if(aux < 0.54) {
			return 5;
		}else if(aux < 0.63) {
			return 6;
		}else if(aux < 0.72) {
			return 7;
		}else if(aux < 0.81) {
			return 8;
		}else if(aux < 0.90) {
			return 9;
		}
		return 10;
	}

	private void generaDatos() {
		int descanso = 1;
		for(int i = 4; i < 11; i++) {
			int dias = (i == 4 || i == 6 || i == 9) ? 30 : 31;

			boolean bandera = false; // Sirve para poder distinguir cuando toca la liga Nacional o Americana
			for(int j = 1; j <= dias; j++) {
				if (descanso == 4) {descanso = 1; bandera = (bandera) ? false : true; continue;}

				// Reordena los Arrays de los equipos
				Collections.shuffle(eqNacional);
                Collections.shuffle(eqAmericana);

				for(int h = 0; h < eqNacional.size(); h++) {
					int carrNacional = calculaCarreras();
					int carrAmericana = calculaCarreras();

					double[] valores = new double[datos.numAttributes()];

					valores[0] = j;
					valores[1] = i;
					if(bandera) {
						valores[2] = ligas.indexOf("Americana");
						valores[3] = equipos.indexOf(eqAmericana.get(h));
						valores[4] = equipos.indexOf(eqNacional.get(h));
						valores[6] = carrAmericana;
						valores[7] = carrNacional;
					}else {
						valores[2] = ligas.indexOf("Nacional");
						valores[3] = equipos.indexOf(eqNacional.get(h));
						valores[4] = equipos.indexOf(eqAmericana.get(h));
						valores[6] = carrNacional;
						valores[7] = carrAmericana;
					}

					if(carrNacional == carrAmericana) {
						valores[5] = ganadores.indexOf("Empate");
					}else if(carrNacional > carrAmericana) {
						valores[5] = ganadores.indexOf(eqNacional.get(h));
					}else {
						valores[5] = ganadores.indexOf(eqAmericana.get(h));
					}

					datos.add(new DenseInstance(1.0, valores));
				}
				descanso++;
			}
		}
		System.out.println(datos.toString());
	}

	private void creaAtributos() {
		atributos = new ArrayList<>();
		atributos.add(new Attribute("Dia"));
		atributos.add(new Attribute("Mes"));
		atributos.add(new Attribute("Liga", ligas));
		atributos.add(new Attribute("Local", equipos));
		atributos.add(new Attribute("Visitante", equipos));
		atributos.add(new Attribute("Ganador", ganadores));
		atributos.add(new Attribute("CarreraLocal"));
		atributos.add(new Attribute("CarreraVisita"));

		datos = new Instances("PartidosBeis", atributos, 0);
	}

    public static void main(String[] args) throws Exception {
		/*ArrayList<Attribute>	atts;
		ArrayList<Attribute>	attsRel;
		ArrayList<String>		attVals;
		ArrayList<String>		attValsRel;
		Instances			data;
		Instances			dataRel;
		double[]			vals;
		double[]			valsRel;
		int				i;

		// 1. set up attributes
		atts = new ArrayList<>();
		// - numeric
		atts.add(new Attribute("att1"));
		// - nominal
		attVals = new ArrayList<>();
		for (i = 0; i < 5; i++)
			attVals.add("val" + (i+1));
		atts.add(new Attribute("att2", attVals));
		// - string
		atts.add(new Attribute("att3", (ArrayList<String>) null));
		// - date
		atts.add(new Attribute("att4", "yyyy-MM-dd"));
		// - relational
		attsRel = new ArrayList<>();
		// -- numeric
		attsRel.add(new Attribute("att5.1"));
		// -- nominal
		attValsRel = new ArrayList<>();
		for (i = 0; i < 5; i++)
			attValsRel.add("val5." + (i+1));
		attsRel.add(new Attribute("att5.2", attValsRel));
		dataRel = new Instances("att5", attsRel, 0);
		atts.add(new Attribute("att5", dataRel, 0));

		// 2. create Instances object
		data = new Instances("MiRelacion", atts, 0);

		// 3. fill with data
		// first instance
		vals = new double[data.numAttributes()];
		// - numeric
		vals[0] = Math.PI;
		// - nominal
		vals[1] = attVals.indexOf("val3");
		// - string
		vals[2] = data.attribute(2).addStringValue("This is a string!");
		// - date
		vals[3] = data.attribute(3).parseDate("2001-11-09");
		// - relational
		dataRel = new Instances(data.attribute(4).relation(), 0);
		// -- first instance
		valsRel = new double[2];
		valsRel[0] = Math.PI + 1;
		valsRel[1] = attValsRel.indexOf("val5.3");
		dataRel.add(new DenseInstance(1.0, valsRel));
		// -- second instance
		valsRel = new double[2];
		valsRel[0] = Math.PI + 2;
		valsRel[1] = attValsRel.indexOf("val5.2");
		dataRel.add(new DenseInstance(1.0, valsRel));
		vals[4] = data.attribute(4).addRelation(dataRel);
		// add
		data.add(new DenseInstance(1.0, vals));

		// second instance
		vals = new double[data.numAttributes()];  // important: needs NEW array!
		// - numeric
		vals[0] = Math.E;
		// - nominal
		vals[1] = attVals.indexOf("val1");
		// - string
		vals[2] = data.attribute(2).addStringValue("And another one!");
		// - date
		vals[3] = data.attribute(3).parseDate("2000-12-01");
		// - relational
		dataRel = new Instances(data.attribute(4).relation(), 0);
		// -- first instance
		valsRel = new double[2];
		valsRel[0] = Math.E + 1;
		valsRel[1] = attValsRel.indexOf("val5.4");
		dataRel.add(new DenseInstance(1.0, valsRel));
		// -- second instance
		valsRel = new double[2];
		valsRel[0] = Math.E + 2;
		valsRel[1] = attValsRel.indexOf("val5.1");
		dataRel.add(new DenseInstance(1.0, valsRel));
    	vals[4] = data.attribute(4).addRelation(dataRel);
    	// add
    	data.add(new DenseInstance(1.0, vals));

    	// 4. output data
    	System.out.println(data);*/

		ArrayList<String> eNacional = new ArrayList<>();
        ArrayList<String> eAmericana = new ArrayList<>();

        eNacional.add("Dodgers");
        eNacional.add("Bravos");
        eNacional.add("Cardenales");
        eNacional.add("Gigantes");
        eNacional.add("Cachorros");

        eAmericana.add("Yankees");
        eAmericana.add("Astros");
        eAmericana.add("Piratas");
        eAmericana.add("Medias rojas");
        eAmericana.add("Cerveceros");

		new Principal(eNacional, eAmericana);
	}
    
}
