package com.ecommerce.auth.infraestructure.entry_points;


import com.ecommerce.auth.domain.model.Usuario;
import com.ecommerce.auth.domain.usecase.UsuarioUseCase;
import com.ecommerce.auth.infraestructure.driver_adapters.UsuarioData;
import com.ecommerce.auth.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/api/ecommerce/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> datosInvalidos(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @PostMapping("/save")
    public ResponseEntity<Usuario> saveUsuario(@RequestBody UsuarioData usuarioData){
        Usuario usuario = usuarioMapper.toUsuario(usuarioData);
        Usuario usuarioGuardado = usuarioUseCase.guardarUsuario(usuario);

        return new ResponseEntity<>(usuarioGuardado, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable String id){
        Usuario usuario = usuarioUseCase.buscarUsuarioPorId(id);

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarUsuarioPorCorreo(@PathVariable String correo){
        Usuario usuario = usuarioUseCase. buscarUsuarioPorCorreo(correo);

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody UsuarioData usuarioData){
        Usuario usuario = usuarioMapper.toUsuario(usuarioData);
        Usuario usuarioActualizado = usuarioUseCase.actualizarUsuario(usuario);

        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id){
        usuarioUseCase.eliminarUsuario(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioData usuarioData){
        try{
            String mensajeRespuesta= usuarioUseCase.loginUsuario(usuarioData.getCorreo(),usuarioData.getPassword());
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);

        } catch (Exception error) {
            error.printStackTrace();
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal>consultarSaldo(@PathVariable String id){
        BigDecimal saldo = usuarioUseCase.consultarSaldo(id);
        return ResponseEntity.ok(saldo);
    }
    @PostMapping("/{id}/recargar")
    public ResponseEntity<String> recargarSaldo(@PathVariable String id, @RequestBody Map<String, BigDecimal> request){
        BigDecimal monto = request.get("monto");
        usuarioUseCase.recargarSaldo(id, monto);
        return ResponseEntity.ok("Saldo recargado exitosamente");
    }

    @PostMapping("/{id}/descontar")
    public ResponseEntity<String> descontarSaldo(@PathVariable String id, @RequestBody Map<String, BigDecimal> request) {
        BigDecimal monto = request.get("monto");
        usuarioUseCase.descontarSaldo(id, monto);
        return ResponseEntity.ok("Descuento de saldo realizado correctamente");
    }
}


