package com.josesantos.eSports.api;

import com.josesantos.eSports.entity.UsuarioEntity;
import com.josesantos.eSports.repository.TipoUsuarioRepository;
import com.josesantos.eSports.repository.UsuarioRepository;
import java.util.ArrayList;
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

/**
 *
 * @author Saslt
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    TipoUsuarioRepository oTipoUsuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(), HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {

        return new ResponseEntity<List<UsuarioEntity>>(oUsuarioRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);

        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 8, direction = Sort.Direction.ASC) Pageable oPageable,
            @RequestParam(name = "equipo", required = false) Long equipo, @RequestParam(name = "filtertype", required = false) String filtertype) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Page<UsuarioEntity> oPage;
        if (equipo != null) {
            oPage = oUsuarioRepository.findByEquipoId(equipo, oPageable);
            System.out.println("Filtrado");

        } else if (filtertype != null) {
            oPage = oUsuarioRepository.findByLoginIgnoreCaseContainingOrSummonernameIgnoreCaseContainingOrEmailIgnoreCaseContainingOrTwitterIgnoreCaseContainingOrDiscordIgnoreCaseContaining(
                    filtertype, filtertype, filtertype, filtertype, filtertype, oPageable);
        } else {
            oPage = oUsuarioRepository.findAll(oPageable);
            System.out.println("getAll");
        }

        return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);

    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody UsuarioEntity oNewUsuarioEntity) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {

            if (oNewUsuarioEntity.getId() == null && this.oTipoUsuarioRepository.existsById(3L)) {
                oNewUsuarioEntity.setTipousuario(this.oTipoUsuarioRepository.findById(3L).get());
                
                if(!comprobarvalidez(oNewUsuarioEntity)){
                
                                return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
                System.out.println(oNewUsuarioEntity.getPassword());
                //oNewUsuarioEntity.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
                return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
            } else {
                return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
            }
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) {
                if (oNewUsuarioEntity.getId() == null) {
                    
                    //oNewUsuarioEntity.setPassword("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
                    return new ResponseEntity<>(oUsuarioRepository.save(oNewUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }
    
    public boolean comprobarvalidez(UsuarioEntity usuario){
        
        try {
             UsuarioEntity dbUser = oUsuarioRepository.findByLogin(usuario.getLogin());
             
             if(dbUser==null){
             return true;
             }
             if(usuario.getLogin().equals(dbUser.getLogin())){
                 return false;
             }
             

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {

        UsuarioEntity oUsuarioEntity2 = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        System.out.println(oUsuarioEntity.getEquipo().getId());
        String password = oUsuarioEntity2.getPassword();
        if (oUsuarioEntity2 == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity2.getTipousuario().getId() == 1) {
                if (oUsuarioRepository.existsById(id)) {
                    oUsuarioEntity.setId(id);
                    if (oUsuarioEntity2.getId() == id) {
                        oUsuarioEntity.setPassword(password);
                    }

                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.findById(id).get(),
                            HttpStatus.NOT_FOUND);
                }
            } else {
                if (oUsuarioEntity2.getId() == id) {
                    oUsuarioEntity.setId(oUsuarioEntity2.getId());
                    oUsuarioEntity.setPassword(password);
                    return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if (oUsuarioEntity.getTipousuario().getId() == 1) {
            oUsuarioRepository.deleteById(id);
        } else if (oUsuarioEntity.getId() == id) {
            oUsuarioRepository.deleteById(id);
        }
        if (oUsuarioRepository.existsById(id)) {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }

    }

}
