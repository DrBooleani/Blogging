export interface PostResponse {
  id: number;
  title: string;
  thumbnail: string;
  content?: string;
  category: string;
  tags: string[];
  createdAt: string;
  updatedAt: string;
}