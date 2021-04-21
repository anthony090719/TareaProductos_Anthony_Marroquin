package com.example.demo.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Producto;
import com.example.demo.reposities.ProductRepository;

@RestController 
@RequestMapping(value = "/productos")
public class ProductController {
	
	@Autowired 
	ProductRepository repository;
	
	@GetMapping 
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<Producto>getListaProductos(){
		Iterable<Producto> listaproductos = repository.findAll();
		
		return (Collection<Producto>) listaproductos;
	}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Producto getProducto(@PathVariable(name = "id")Long id) {
		
		Optional<Producto> producto = repository.findById(id);
		Producto result = null;
		if (producto.isPresent()) {
			result = producto.get();
		}
		return result;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto createProducto(@RequestBody Producto producto ) {
		Producto nuevoProducto = repository.save(producto);
		return nuevoProducto;
		
	}
	@DeleteMapping(value = "/(id)")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void deleteProducto(@PathVariable(name = "id") Long id) {
		repository.deleteById(id);
		
	}
	
	@PutMapping(value = "/(id)")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public Producto updateProduct(@PathVariable (name="id")Long id,
			@RequestBody Producto producto) {
		Optional<Producto> oProducto = repository.findById(id);
		if (oProducto.isPresent()) {
			Producto actual = oProducto.get();
			actual.setId(id);
			actual.setName(producto.getName());
			actual.setPrice(producto.getPrice());
			Producto updatedProduct = repository.save(actual);
			return updatedProduct;
		}
		return null;
	}

}
