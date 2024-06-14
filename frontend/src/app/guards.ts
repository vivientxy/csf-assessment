import { ActivatedRouteSnapshot, CanDeactivateFn, Router, RouterStateSnapshot } from "@angular/router";
import { PictureComponent } from "./views/picture.component";
import { Observable } from "rxjs";
import { inject } from "@angular/core";

export const leavePicture: CanDeactivateFn<PictureComponent> =
    (comp: PictureComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
            : boolean | Promise<boolean> | Observable<boolean> => {

        const router = inject(Router)
        return confirm('You have not saved your picture. Are you sure you want to leave?')
    }