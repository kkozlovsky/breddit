import {Component, OnInit} from '@angular/core';
import {PostService} from 'src/app/shared/post.service';
import {ActivatedRoute} from '@angular/router';
import {PostModel} from 'src/app/shared/post-model';
import {throwError} from 'rxjs';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CommentPayload} from 'src/app/comment/comment.payload';
import {CommentService} from 'src/app/comment/comment.service';
import {AuthService} from "../../auth/shared/auth.service";

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId: number;
  post: PostModel;
  username: string;
  commentForm: FormGroup;
  commentPayload: CommentPayload;
  comments: CommentPayload[];

  constructor(private postService: PostService,
              private activateRoute: ActivatedRoute,
              private commentService: CommentService,
              private authService: AuthService
  ) {
  }

  ngOnInit(): void {
    this.postId = this.activateRoute.snapshot.params.id;

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });

    this.commentPayload = {
      text: '',
      postId: this.postId,
      username: this.authService.getUsername()
    };

    this.getPostById();
    this.getCommentsForPost();
  }

  postComment() {
    this.commentPayload.text = this.commentForm.get('text').value;

    this.commentService.postComment(this.commentPayload).subscribe(() => {
      this.commentForm.get('text').setValue('');
      this.getCommentsForPost();
    }, error => {
      throwError(error);
    })
  }

  private getPostById() {
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    }, error => {
      throwError(error);
    });
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.postId).subscribe(data => {
      this.comments = data;
    }, error => {
      throwError(error);
    });
  }

}
