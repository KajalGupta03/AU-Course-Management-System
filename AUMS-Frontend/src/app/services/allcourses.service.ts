import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { courses } from '../ApiResponse';

@Injectable({
  providedIn: 'root',
})
export class AllcoursesService {
  courseid: any;
  constructor(private http: HttpClient) {}

  courses(): Observable<any> {
    return this.http.get('http://localhost:8080/course/all');
  }

  getCourseById(key: any): Observable<any> {
    this.courseid = key;
    return this.http.get(
      'http://localhost:8080/course/getById/' + this.courseid
    );
  }

  getSkillByCourseId(key: any): Observable<any> {
    this.courseid = key;
    return this.http.get(
      'http://localhost:8080/course/getAllSkills/' + this.courseid
    );
  }

  getFeedbackByCourseId(key: any): Observable<any> {
    this.courseid = key;
    return this.http.get('http://localhost:8080/feedback/all/' + this.courseid);
  }

  public editCourse(data: courses) {
    console.log(data);
    this.http
      .put<any>(
        'http://localhost:8080/course/updateCourse/' + data.courseId,
        data
      )
      .subscribe((data) => console.log(data));
  }

  addCourse(data: courses) {
    console.log(data);
    return this.http.post<any>('http://localhost:8080/course/addCourse/', data);
  }

  deleteCourseById(key: any): Observable<any> {
    this.courseid = key;
    return this.http.delete<any>(
      'http://localhost:8080/course/deleteCourse/' + this.courseid
    );
  }
}
