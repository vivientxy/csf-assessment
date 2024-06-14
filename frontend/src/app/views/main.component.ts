import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { WebcamImage, WebcamInitError, WebcamUtil } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {

  // TODO: Task 1

  private readonly router = inject(Router)

  width: number = 500;
  height: number = 282;
  public videoOptions: MediaTrackConstraints = {
    width: {exact: 500},
    height: {exact: 282},
    aspectRatio: {exact: 500/282}
  };
  // captured picture data
  public webcamImage: WebcamImage | null = null;
  private trigger: Subject<void> = new Subject<void>();
  public errors: WebcamInitError[] = [];
  emitPicture = new Subject<ImageData>();

  public ngOnInit(): void {

  }

  public snap(): void {
    this.trigger.next();
    if (this.webcamImage !== null)
      this.emitPicture.next(this.webcamImage.imageData)
    this.router.navigate(['/picture'])
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  public handleInitError(error: WebcamInitError): void {
    this.errors.push(error);
  }

  public handleImage(webcamImage: WebcamImage): void {
    console.info('received webcam image', webcamImage);
    console.info('to pass this data:', webcamImage.imageAsDataUrl);
    this.webcamImage = webcamImage;
  }


  changeHeight(height: number) {
    console.log('>>> current height:', this.videoOptions.height, this.videoOptions.aspectRatio)
    this.videoOptions.height = {exact: height};
    this.videoOptions.aspectRatio = {exact: 500/height};
    this.height = height;
    console.log('>>> new height:', this.videoOptions.height, this.videoOptions.aspectRatio)
  }

}