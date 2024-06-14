import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { dataToImage } from '../utils';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit {

  // TODO: Task 2
  // TODO: Task 3

  private readonly svc = inject(UploadService)
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
      content: this.fb.control,
      title: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      comments: this.fb.control('')
    })
  }

  back() {
    this.router.navigate(['/'])
  }

  isPictureNotSaved() {
    return this.pictureUrl !== '';
  }

  processSubmit() {
    const formData = new FormData();
    
    // reduce overhead of multipart form data -- combine the various fields into a pipe-separated string
    const content = this.form.controls['title'].value + '|' + this.form.controls['comments'].value + '|' + new Date()
    formData.append('picture', dataToImage(this.pictureUrl))
    formData.append('content', content)

    // send data to springboot
    this.svc.upload(formData).subscribe({
      next: resp => {
        this.pictureUrl = '';
        this.router.navigate(['/'])
      },
      error: err => {alert(err.error.message)}
    })
  }

}
