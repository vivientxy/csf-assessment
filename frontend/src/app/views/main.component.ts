import { Component, OnInit, Output, inject } from '@angular/core';
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
  // public videoOptions: MediaTrackConstraints = {
  //   width: {exact: 500},
  //   height: {exact: 282},
  //   aspectRatio: {exact: 500/282}
  // };
  public webcamImage: WebcamImage | null = null;
  private trigger: Subject<void> = new Subject<void>();
  public errors: WebcamInitError[] = [];

  public ngOnInit(): void {

  }

  public snap(): void {
    this.trigger.next();
    if (this.webcamImage !== null)
      this.router.navigate(['/picture',this.webcamImage.imageAsDataUrl])
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


  changeHeight(event: any) {
    // console.log('>>> current height:', this.videoOptions.height, this.videoOptions.aspectRatio)

    // this.videoOptions.height = {exact: height};
    // this.videoOptions.aspectRatio = {exact: 500/height};
    // this.height = height;
    // console.log('>>> new height:', this.videoOptions.height, this.videoOptions.aspectRatio)
    console.log('>>> new height:', event.target.value)
    // this.videoOptions.height = {exact: event.target.value};
    // this.videoOptions.aspectRatio = {exact: 500/event.target.value};
    this.height = event.target.value;
    this.width = 500;

  }

}