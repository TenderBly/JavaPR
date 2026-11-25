CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    genre VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    file_size_mb DOUBLE PRECISION,
    file_format VARCHAR(50),
    page_count INTEGER,
    weight_kg DOUBLE PRECISION,
    narrator VARCHAR(255),
    duration_minutes INTEGER,
    has_video_content BOOLEAN,
    has_quizzes BOOLEAN
);