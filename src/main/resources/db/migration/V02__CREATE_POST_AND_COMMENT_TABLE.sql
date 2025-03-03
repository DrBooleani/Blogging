CREATE TABLE tb_post (
  id SERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  thumbnail VARCHAR(255) DEFAULT 'no-thumbnail.png',
  content TEXT NOT NULL,
  category VARCHAR(100) NOT NULL,
  tags TEXT[],
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_comment (
  id SERIAL PRIMARY KEY,
  content TEXT NOT NULL,
  user_id INT NOT NULL,
  post_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE,
  FOREIGN KEY (post_id) REFERENCES tb_post(id) ON DELETE CASCADE
);