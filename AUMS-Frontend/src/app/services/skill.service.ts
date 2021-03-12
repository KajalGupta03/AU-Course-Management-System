import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { skills } from './../ApiResponse';
@Injectable({
  providedIn: 'root',
})
export class SkillService {
  courseId: any;
  skillId: any;
  constructor(private http: HttpClient) {}

  getAllSkills(): Observable<any> {
    return this.http.get('http://localhost:8080/course/getAllSkillsAll');
  }

  addSkillInCourse(key1: any, key2: any): Observable<any> {
    this.courseId = key1;
    this.skillId = key2;
    return this.http.post<any>(
      'http://localhost:8080/course/addSkillInCourse/' +
        this.courseId +
        '/' +
        this.skillId,
      ''
    );
  }

  getSkillInCourse(key1: any): Observable<any> {
    this.courseId = key1;
    return this.http.get<any>(
      'http://localhost:8080/course/getAllSkills/' +
        this.courseId
    );
  }
}
