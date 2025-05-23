package trabajoPractico;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Ticketek implements ITicketek {
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Espectaculo> espectaculos;
	private HashMap<String, Sede> sedes;
	private HashMap<String, Usuario> usuariosDeEntrada;
	
	Ticketek(){
		this.usuarios = new HashMap<String, Usuario>();
		this.espectaculos = new HashMap<String, Espectaculo>();
		this.sedes = new HashMap<String, Sede>();
		this.usuariosDeEntrada = new HashMap<String, Usuario>();
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		Sede sede = new Estadio(nombre,direccion,capacidadMaxima);
		sedes.put(nombre, sede);
			

	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		Sede sede = new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		if(sedes.containsKey(nombre)) {
			throw new RuntimeException("Error: El nombre de la sede ya esta registrado");
		}
		if(capacidadMaxima <=0) {
			throw new RuntimeException("Error: la capacidad maxima no puede ser negativa o 0.");
		}
		Sede sede = new MiniEstadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
		sedes.put(nombre, sede);
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		if(espectaculos.containsKey(nombre)) {
			throw new RuntimeException("El nombre del espectaculo ya esta registrado");
		}
		Espectaculo espectaculo = new Espectaculo(nombre);
		espectaculos.put(nombre, espectaculo);
	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fechaString, String sedeString, double precioBase) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		if(!sedes.containsKey(sedeString)) 
			throw new RuntimeException("El nombre de la sede no esta registrado.");
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Sede sede = sedes.get(sedeString);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		espectaculo.agregarFuncion(fecha, sede, precioBase);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			int cantidadEntradas) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		return espectaculo.venderEntrada(nombreEspectaculo, fecha, cantidadEntradas);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fechaString, String email, String contrasenia,
			String sector, int[] asientos) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		return espectaculo.venderEntrada(nombreEspectaculo, fecha, sector, asientos);
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarFunciones();
	}

	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.listarEntradas();
	}

	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString ) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha);
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fechaString, String sector) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		
		Fecha fechaObjeto = new Fecha(fechaString);
		LocalDate fecha = fechaObjeto.obtenerFecha();
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.costoEntrada(fecha,sector);
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado();
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		if(!espectaculos.containsKey(nombreEspectaculo)) 
			throw new RuntimeException("El nombre del espectaculo no esta registrado.");
		
		if(!sedes.containsKey(nombreSede))
			throw new RuntimeException("El nombre de la sede no esta registrado.");
		
		Sede sede = sedes.get(nombreSede);
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
		return espectaculo.totalRecaudado(sede);
	}
}
