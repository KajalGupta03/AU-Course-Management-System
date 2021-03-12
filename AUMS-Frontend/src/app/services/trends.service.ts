import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TrendsService {

  constructor(private http: HttpClient) { }

  getTrendingSkills(): Observable<any> {
    return this.http.get('http://localhost:8080/trends/getTrendingSkills');
  }

  getCourseRating(): Observable<any> {
    return this.http.get('http://localhost:8080/trends/getCourseRating');
  }
}
