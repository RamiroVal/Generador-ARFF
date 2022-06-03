package codigo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private ArrayList<String> ganadores;
	private Instances datos;

	public Principal(ArrayList<String> eqNacional, ArrayList<String> eqAmericana) {
		this.eqNacional = eqNacional;
		this.eqAmericana = eqAmericana;

		ganadores = new ArrayList<>();
		ganadores.addAll(eqNacional);
		ganadores.addAll(eqAmericana);
		
		ligas = new ArrayList<>();
		ligas.add("Nacional");
		ligas.add("Americana");
		
		creaAtributos();
		generaDatos();
		creaArchivo();

	}

	private void creaArchivo() {
		try {
			String arch = "archivos/PartidosBeis.arff";

			File file = new File(arch);

			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(datos.toString());
			bw.close();
			System.out.println("Archivo creado con éxito.");
		}catch(Exception e){
			System.err.println("Hubo un error.");
			e.printStackTrace();
		}
	}

	private void generaDatos() {
		int descanso = 1;
		ArrayList<String> eq1 = new ArrayList<>();
		ArrayList<String> eq2 = new ArrayList<>();
		eq1.addAll(eqNacional);
		eq2.addAll(eqAmericana);

		for(int i = 4; i < 11; i++) {
			int dias = (i == 4 || i == 6 || i == 9) ? 30 : 31;

			boolean bandera = false; // Sirve para poder distinguir cuando toca la liga Nacional o Americana
			for(int j = 1; j <= dias; j++) {
				if (descanso == 4) {descanso = 1; bandera = (bandera) ? false : true; continue;}

				// Reordena los Arrays de los equipos
				Collections.shuffle(eq1);
                Collections.shuffle(eq2);

				for(int h = 0; h < eqNacional.size(); h++) {
					int carrNacional, carrAmericana = carrNacional = 0;

					while(carrNacional == carrAmericana) {
						carrNacional = (int)(Math.random() * 11);
						carrAmericana = (int)(Math.random() * 11);
					}
					double[] valores = new double[datos.numAttributes()];
					valores[0] = j;
					valores[1] = i;
					valores[2] = (bandera) ? ligas.indexOf("Americana") : ligas.indexOf("Nacional");
					valores[3] = eqNacional.indexOf(eq1.get(h));
					valores[4] = eqAmericana.indexOf(eq2.get(h));
					valores[5] = (carrNacional > carrAmericana) ? ganadores.indexOf(eq1.get(h)) : ganadores.indexOf(eq2.get(h));
					valores[6] = carrNacional;
					valores[7] = carrAmericana;

					datos.add(new DenseInstance(1.0, valores));
				}
				descanso++;
			}
		}
	}

	private void creaAtributos() {
		atributos = new ArrayList<>();
		atributos.add(new Attribute("Dia"));
		atributos.add(new Attribute("Mes"));
		atributos.add(new Attribute("Liga", ligas));
		atributos.add(new Attribute("Equipo 1", eqNacional));
		atributos.add(new Attribute("Equipo 2", eqAmericana));
		atributos.add(new Attribute("Ganador", ganadores));
		atributos.add(new Attribute("Carrera Equipo 1"));
		atributos.add(new Attribute("Carrera Equipo 2"));

		datos = new Instances("PartidosBeis", atributos, 0);
	}

    public static void main(String[] args) throws Exception {
		ArrayList<String> eNacional = new ArrayList<>();
        ArrayList<String> eAmericana = new ArrayList<>();

        eNacional.add("Dodgers");
        eNacional.add("Bravos");
        eNacional.add("Cardenales");
        //eNacional.add("Gigantes");
        //eNacional.add("Cachorros");

        eAmericana.add("Yankees");
        eAmericana.add("Astros");
        eAmericana.add("Piratas");
        //eAmericana.add("Medias rojas");
        //eAmericana.add("Cerveceros");

		new Principal(eNacional, eAmericana);
	}
    
}
