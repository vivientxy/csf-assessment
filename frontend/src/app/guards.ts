import { ActivatedRouteSnapshot, CanDeactivateFn, RouterStateSnapshot } from "@angular/router";
import { PictureComponent } from "./views/picture.component";
import { Observable } from "rxjs";

export const leavePicture: CanDeactivateFn<PictureComponent> =
    (comp: PictureComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
            : boolean | Promise<boolean> | Observable<boolean> => {

        if (comp.isPictureNotSaved())
            return confirm('You have not saved your picture. Are you sure you want to leave?')
        return true;
    }