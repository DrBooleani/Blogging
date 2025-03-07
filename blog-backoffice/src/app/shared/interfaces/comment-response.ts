export interface CommentResponse {
  editing?: boolean;
  id: number;
  content: string;
  userId: number;
  postId: number;
  postTitle: string;
  createdAt: string;
  updatedAt: string;
  fullName: string;
  profilePicture: string;
}