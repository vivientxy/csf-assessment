import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { dataToImage } from '../utils';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit {

  // TODO: Task 2
  // TODO: Task 3

  private readonly http = inject(HttpClient)
  private readonly router = inject(Router)
  private readonly fb = inject(FormBuilder)
  private readonly activatedRoute = inject(ActivatedRoute)

  form!: FormGroup
  pictureUrl!: string;

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
        params => {this.pictureUrl = params['pictureUrl']
    })    
    this.form = this.fb.group({
      picture: this.fb.control,
      title: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      comments: this.fb.control(''),
      datetime: this.fb.control
    })
  }

  back() {
    this.router.navigate(['/'])
  }

  processSubmit() {
    // picture as JavaScript File object
    // current datetime
    this.form.controls['picture'].setValue(dataToImage(this.pictureUrl))
    this.form.controls['datetime'].setValue(new Date())
    console.log('>>> form values:', this.form.value)
    // send data to springboot

    // reduce overhead of multipart form data -- concat the fields into csv string



  }

}
