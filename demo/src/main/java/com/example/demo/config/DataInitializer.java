package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.Model.Ciudad;
import com.example.demo.Model.Departamento;
import com.example.demo.repository.CiudadRepository;
import com.example.demo.repository.DepartamentoRepository;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final DepartamentoRepository departamentoRepository;
    private final CiudadRepository ciudadRepository;

    @Autowired
    public DataInitializer(DepartamentoRepository departamentoRepository, CiudadRepository ciudadRepository) {
        this.departamentoRepository = departamentoRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializa si no hay departamentos
        if (departamentoRepository.count() == 0) {
            // Crear departamentos
            Departamento amazonas = new Departamento();
            amazonas.setNombre("Amazonas");
            
            Departamento antioquia = new Departamento();
            antioquia.setNombre("Antioquia");
            
            Departamento arauca = new Departamento();
            arauca.setNombre("Arauca");
            
            Departamento atlantico = new Departamento();
            atlantico.setNombre("Atlántico");
            
            Departamento bolivar = new Departamento();
            bolivar.setNombre("Bolívar");
            
            Departamento boyaca = new Departamento();
            boyaca.setNombre("Boyacá");
            
            Departamento caldas = new Departamento();
            caldas.setNombre("Caldas");
            
            Departamento caqueta = new Departamento();
            caqueta.setNombre("Caquetá");
            
            Departamento casanare = new Departamento();
            casanare.setNombre("Casanare");
            
            Departamento cauca = new Departamento();
            cauca.setNombre("Cauca");
            
            Departamento cesar = new Departamento();
            cesar.setNombre("Cesar");
            
            Departamento choco = new Departamento();
            choco.setNombre("Chocó");
            
            Departamento cordoba = new Departamento();
            cordoba.setNombre("Córdoba");
            
            Departamento cundinamarca = new Departamento();
            cundinamarca.setNombre("Cundinamarca");
            
            Departamento guainia = new Departamento();
            guainia.setNombre("Guainía");
            
            Departamento guaviare = new Departamento();
            guaviare.setNombre("Guaviare");
            
            Departamento huila = new Departamento();
            huila.setNombre("Huila");
            
            Departamento guajira = new Departamento();
            guajira.setNombre("La Guajira");
            
            Departamento magdalena = new Departamento();
            magdalena.setNombre("Magdalena");
            
            Departamento meta = new Departamento();
            meta.setNombre("Meta");
            
            Departamento narino = new Departamento();
            narino.setNombre("Nariño");
            
            Departamento norteSantander = new Departamento();
            norteSantander.setNombre("Norte de Santander");
            
            Departamento putumayo = new Departamento();
            putumayo.setNombre("Putumayo");
            
            Departamento quindio = new Departamento();
            quindio.setNombre("Quindío");
            
            Departamento risaralda = new Departamento();
            risaralda.setNombre("Risaralda");
            
            Departamento sanAndres = new Departamento();
            sanAndres.setNombre("San Andrés y Providencia");
            
            Departamento santander = new Departamento();
            santander.setNombre("Santander");
            
            Departamento sucre = new Departamento();
            sucre.setNombre("Sucre");
            
            Departamento tolima = new Departamento();
            tolima.setNombre("Tolima");
            
            Departamento valle = new Departamento();
            valle.setNombre("Valle del Cauca");
            
            Departamento vaupes = new Departamento();
            vaupes.setNombre("Vaupés");
            
            Departamento vichada = new Departamento();
            vichada.setNombre("Vichada");
            
            // Guardar departamentos
            amazonas = departamentoRepository.save(amazonas);
            antioquia = departamentoRepository.save(antioquia);
            arauca = departamentoRepository.save(arauca);
            atlantico = departamentoRepository.save(atlantico);
            bolivar = departamentoRepository.save(bolivar);
            boyaca = departamentoRepository.save(boyaca);
            caldas = departamentoRepository.save(caldas);
            caqueta = departamentoRepository.save(caqueta);
            casanare = departamentoRepository.save(casanare);
            cauca = departamentoRepository.save(cauca);
            cesar = departamentoRepository.save(cesar);
            choco = departamentoRepository.save(choco);
            cordoba = departamentoRepository.save(cordoba);
            cundinamarca = departamentoRepository.save(cundinamarca);
            guainia = departamentoRepository.save(guainia);
            guaviare = departamentoRepository.save(guaviare);
            huila = departamentoRepository.save(huila);
            guajira = departamentoRepository.save(guajira);
            magdalena = departamentoRepository.save(magdalena);
            meta = departamentoRepository.save(meta);
            narino = departamentoRepository.save(narino);
            norteSantander = departamentoRepository.save(norteSantander);
            putumayo = departamentoRepository.save(putumayo);
            quindio = departamentoRepository.save(quindio);
            risaralda = departamentoRepository.save(risaralda);
            sanAndres = departamentoRepository.save(sanAndres);
            santander = departamentoRepository.save(santander);
            sucre = departamentoRepository.save(sucre);
            tolima = departamentoRepository.save(tolima);
            valle = departamentoRepository.save(valle);
            vaupes = departamentoRepository.save(vaupes);
            vichada = departamentoRepository.save(vichada);

            // Crear ciudades
            Ciudad[] ciudades = {
                // Amazonas
                new Ciudad(0, "Leticia", "", amazonas),
                new Ciudad(0, "Puerto Nariño", "", amazonas),
                
                // Antioquia
                new Ciudad(0, "Medellín", "", antioquia),
                new Ciudad(0, "Bello", "", antioquia),
                new Ciudad(0, "Envigado", "", antioquia),
                new Ciudad(0, "Itagüí", "", antioquia),
                new Ciudad(0, "Rionegro", "", antioquia),
                
                // Arauca
                new Ciudad(0, "Arauca", "", arauca),
                new Ciudad(0, "Saravena", "", arauca),
                
                // Atlántico
                new Ciudad(0, "Barranquilla", "", atlantico),
                new Ciudad(0, "Soledad", "", atlantico),
                new Ciudad(0, "Malambo", "", atlantico),
                
                // Bolívar
                new Ciudad(0, "Cartagena", "", bolivar),
                new Ciudad(0, "Turbaco", "", bolivar),
                new Ciudad(0, "Magangué", "", bolivar),
                
                // Boyacá
                new Ciudad(0, "Tunja", "", boyaca),
                new Ciudad(0, "Duitama", "", boyaca),
                new Ciudad(0, "Sogamoso", "", boyaca),
                
                // Caldas
                new Ciudad(0, "Manizales", "", caldas),
                new Ciudad(0, "Chinchiná", "", caldas),
                
                // Caquetá
                new Ciudad(0, "Florencia", "", caqueta),
                
                // Casanare
                new Ciudad(0, "Yopal", "", casanare),
                new Ciudad(0, "Aguazul", "", casanare),
                
                // Cauca
                new Ciudad(0, "Popayán", "", cauca),
                new Ciudad(0, "Santander de Quilichao", "", cauca),
                
                // Cesar
                new Ciudad(0, "Valledupar", "", cesar),
                new Ciudad(0, "Aguachica", "", cesar),
                
                // Chocó
                new Ciudad(0, "Quibdó", "", choco),
                
                // Córdoba
                new Ciudad(0, "Montería", "", cordoba),
                new Ciudad(0, "Lorica", "", cordoba),
                
                // Cundinamarca
                new Ciudad(0, "Bogotá", "", cundinamarca),
                new Ciudad(0, "Soacha", "", cundinamarca),
                new Ciudad(0, "Zipaquirá", "", cundinamarca),
                new Ciudad(0, "Facatativá", "", cundinamarca),
                new Ciudad(0, "Chía", "", cundinamarca),
                
                // Guainía
                new Ciudad(0, "Inírida", "", guainia),
                
                // Guaviare
                new Ciudad(0, "San José del Guaviare", "", guaviare),
                
                // Huila
                new Ciudad(0, "Neiva", "", huila),
                new Ciudad(0, "Pitalito", "", huila),
                
                // La Guajira
                new Ciudad(0, "Riohacha", "", guajira),
                new Ciudad(0, "Maicao", "", guajira),
                
                // Magdalena
                new Ciudad(0, "Santa Marta", "", magdalena),
                new Ciudad(0, "Ciénaga", "", magdalena),
                
                // Meta
                new Ciudad(0, "Villavicencio", "", meta),
                new Ciudad(0, "Acacías", "", meta),
                
                // Nariño
                new Ciudad(0, "Pasto", "", narino),
                new Ciudad(0, "Ipiales", "", narino),
                
                // Norte de Santander
                new Ciudad(0, "Cúcuta", "", norteSantander),
                new Ciudad(0, "Ocaña", "", norteSantander),
                
                // Putumayo
                new Ciudad(0, "Mocoa", "", putumayo),
                
                // Quindío
                new Ciudad(0, "Armenia", "", quindio),
                new Ciudad(0, "Calarcá", "", quindio),
                
                // Risaralda
                new Ciudad(0, "Pereira", "", risaralda),
                new Ciudad(0, "Dosquebradas", "", risaralda),
                
                // San Andrés y Providencia
                new Ciudad(0, "San Andrés", "", sanAndres),
                new Ciudad(0, "Providencia", "", sanAndres),
                
                // Santander
                new Ciudad(0, "Bucaramanga", "", santander),
                new Ciudad(0, "Floridablanca", "", santander),
                new Ciudad(0, "Girón", "", santander),
                
                // Sucre
                new Ciudad(0, "Sincelejo", "", sucre),
                new Ciudad(0, "Corozal", "", sucre),
                
                // Tolima
                new Ciudad(0, "Ibagué", "", tolima),
                new Ciudad(0, "Espinal", "", tolima),
                
                // Valle del Cauca
                new Ciudad(0, "Cali", "", valle),
                new Ciudad(0, "Palmira", "", valle),
                new Ciudad(0, "Buenaventura", "", valle),
                new Ciudad(0, "Tuluá", "", valle),
                
                // Vaupés
                new Ciudad(0, "Mitú", "", vaupes),
                
                // Vichada
                new Ciudad(0, "Puerto Carreño", "", vichada)
            };

            try {
                // Guardar todas las ciudades
                List<Ciudad> ciudadesList = Arrays.asList(ciudades);
                ciudadRepository.saveAll(ciudadesList);
                logger.info("✅ Datos iniciales cargados correctamente");
                System.out.println("✅ Datos iniciales cargados correctamente");
            } catch (Exception e) {
                logger.error("Error al cargar los datos iniciales: " + e.getMessage());
                System.err.println("Error al cargar los datos iniciales: " + e.getMessage());
                throw e;
            }
        } else {
            logger.info("Los datos ya están cargados en la base de datos");
            System.out.println("Los datos ya están cargados en la base de datos");
        }
    }
}