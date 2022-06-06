package com.josesantos.eSports.api;

import com.josesantos.eSports.entity.EquipoEntity;
import com.josesantos.eSports.entity.UsuarioEntity;
import com.josesantos.eSports.repository.EquipoRepository;
import com.josesantos.eSports.repository.UsuarioRepository;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

    @Autowired
    EquipoRepository oEquipoRepository;

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        return new ResponseEntity<EquipoEntity>(oEquipoRepository.findById(id).get(), HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        return new ResponseEntity<List<EquipoEntity>>(oEquipoRepository.findAll(), HttpStatus.OK);

    }


    @GetMapping("/count")
    public ResponseEntity<?> count() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<Long>(oEquipoRepository.count(), HttpStatus.OK);

        }
    }
    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 5, direction = Sort.Direction.ASC) Pageable oPageable,
            @RequestParam(required = false) Long filtertype, @RequestParam(required = false) String filter) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Page<EquipoEntity> oPage;
//        if (filtertype != null) {
//            oPage = oUsuarioRepository.findByLoginIgnoreCaseContaining(
//                    filtertype, filter == null ? "" : filter, oPageable);
//            System.out.println("Filtrado");
//
//        } else {
            oPage = oEquipoRepository.findAll(oPageable);
            System.out.println("getAll");
//        }

        return new ResponseEntity<Page<EquipoEntity>>(oPage, HttpStatus.OK);

    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody EquipoEntity oNewEquipoEntity) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                if (oNewEquipoEntity.getId() == null) {
                    //oNewUsuarioEntity.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
                    return new ResponseEntity<>(oEquipoRepository.save(oNewEquipoEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else if (oUsuarioEntity.getTipousuario().getId() == 2) {

                if (oUsuarioEntity.getEquipo().getId() == null) {
                    oUsuarioEntity.setEquipo(oNewEquipoEntity);
                    return new ResponseEntity<>(oEquipoRepository.save(oNewEquipoEntity), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody EquipoEntity oEquipoEntity) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                oEquipoEntity.setId(id);
                return new ResponseEntity<>(oEquipoRepository.save(oEquipoEntity), HttpStatus.OK);
            } else if (oUsuarioEntity.getTipousuario().getId() == 2 && oUsuarioEntity.getEquipo().getId() == id) {

                oEquipoEntity.setId(id);
                return new ResponseEntity<>(oEquipoRepository.save(oEquipoEntity), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                oEquipoRepository.deleteById(id);

            } else if (oUsuarioEntity.getTipousuario().getId() == 2 && oUsuarioEntity.getEquipo().getId() == id) {
                oEquipoRepository.deleteById(id);
            }

            if (oEquipoRepository.existsById(id)) {
                return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.OK);
            }
        }
    }

}
