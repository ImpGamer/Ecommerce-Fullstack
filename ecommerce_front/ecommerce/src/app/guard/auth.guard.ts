import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { SessionStorageService } from "../service/session-storage.service";
/*Un Guard permite el acceso o deniega dependiendo del tipo que se le indica, hacia una ruta mediante una logica
o condicion, de esta manera podemos manejar de mejor manera autorizacion y autenticacion de nuestra aplicacion*/
export const authGuard: CanActivateFn = (route,state) => {
    //Obtenemos el objeto sessionStorage que hemos usado por toda la aplicacion
    const session = inject(SessionStorageService)
    const router = inject(Router)

    if(session.getItem('token') != null) {
        return true
    }

    return router.createUrlTree(['/user/login'])
}