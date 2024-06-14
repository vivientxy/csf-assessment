import { HttpClient } from "@angular/common/http"
import { inject } from "@angular/core"

export class UploadService {

  private readonly http = inject(HttpClient)

  // TODO: Task 3.
  // You may add add parameters to the method
  upload(formData: FormData) {
    return this.http.post('/api/image/upload', formData)
  }
}
