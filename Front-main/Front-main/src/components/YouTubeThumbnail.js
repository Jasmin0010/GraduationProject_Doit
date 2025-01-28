import { Box, Button, Container, TextField } from '@mui/material';
import React, { useState } from 'react';

const YouTubeThumbnail = ({ workoutVideos, addNewVideo, removeVideo }) => {
  const [link, setLink] = useState(''); // 링크 상태 추가
  const [videoId, setVideoId] = useState(''); // 비디오 ID 상태 추가

  const handleChange = (e) => {
    setLink(e.target.value);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault(); // 기본 동작 방지
      onButtonClick(); // 버튼 클릭 이벤트 호출
    }
  };

  const onButtonClick = () => {
    const extractedVideoId = getVideoIdFromLink(link);
    if (extractedVideoId) {
      setVideoId(extractedVideoId); // 비디오 ID 설정
      addNewVideo(extractedVideoId); // 비디오 ID 추가
    }
  }

  // YouTube 링크에서 비디오 ID를 추출하는 함수
  const getVideoIdFromLink = (url) => {
    const match = url.match(/(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/);
    return match ? match[1] : null;
  };

  return (
    <Container>
      <div style={{ marginBottom: '20px' }}>
        <Box display={"flex"} alignItems={"center"}>
          <TextField
            placeholder='Enter YouTube link'
            type='text'
            onChange={handleChange}
            value={link}
            onKeyDown={handleKeyPress}
            fullWidth
            style={{
              width: 100,
              marginLeft: 16,
              marginRight: 16,
              flexGrow: 1,
              flexShrink: 1,
            }}
          />
          <Button
            color='secondary'
            variant='outlined'
            onClick={onButtonClick}
            style={{
              height: 56,
              flexGrow: 0,
              flexShrink: 0,
            }}
          >
            추가
          </Button>
        </Box>
        <div style={{ marginTop: '20px' }}>
          {videoId && (
            <iframe
              title="YouTube Video"
              width="100%"
              // height=""
              // width="560"
              // height="315"
              src={`https://www.youtube.com/embed/${videoId}`}
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            />
          )}
        </div>
        <div>
          {workoutVideos && workoutVideos.map((video, index) => (
            <div key={index}>
              <Button onClick={() => setVideoId(video.videoId)}>{video.videoId}</Button>
              <Button onClick={() => removeVideo(video.id)}>X</Button>
            </div>
          ))}
        </div>
      </div>
    </Container>
  );
};

export default YouTubeThumbnail;
