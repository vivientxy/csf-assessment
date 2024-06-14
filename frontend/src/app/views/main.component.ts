import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamImage, WebcamInitError } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  // TODO: Task 1

  private readonly router = inject(Router)
  public webcamImage: WebcamImage | null = null;
  private trigger: Subject<void> = new Subject<void>();
  public errors: WebcamInitError[] = [];

  width: number = 500;
  height: number = 282;
  public videoOptions: MediaTrackConstraints = {
    width: {exact: this.width},
    height: {exact: this.height},
    aspectRatio: {exact: (this.width/this.height)}
  };

  changeHeight(event: any) {
    this.height = event.target.value;
    this.videoOptions.height = {exact: event.target.value};
    this.videoOptions.aspectRatio = {exact: this.width/event.target.value};

    console.log('>>> height:', this.height)
    console.log('>>> width:', this.width)
    console.log('>>> videoOptions:', this.videoOptions)
  }

  public snap(): void {
    this.trigger.next();
    if (this.webcamImage !== null) {
      this.router.navigate(['/picture',this.webcamImage.imageAsDataUrl])
    }
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  public handleInitError(error: WebcamInitError): void {
    this.errors.push(error);
  }

  public handleImage(webcamImage: WebcamImage): void {
    console.info('received webcam image', webcamImage);
    this.webcamImage = webcamImage;
  }

}