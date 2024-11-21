<template>
    <div class="content-wrap">
        <h2>영상</h2>
        <select @change="requestGetVideo">
            <option v-for="playlist in videoPlaylist" :value="playlist.categoryName">{{ playlist.categoryName }}
            </option>
        </select>
        <div class="content-box">
            <div class="detail h-[500px]">
                <MainVideoListDetail :video="selectedVideo" />
            </div>
            <div
                class="content-scroll my-scrollbar con-title flex justify-between pb-2 mb-6 border-solid border-b-[1px] border-gray-300 w-[100px] h-[50px]">
                <div v-for="video in videoInPlaylist">
                    <img :src="video.thumb" @click="selectedVideo = video" />
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import { ref } from 'vue';
import axios from '@/axios/index';
import MainVideoListDetail from './MainVideoListDetail.vue';
const videoPlaylist = ref([]);
const videoInPlaylist = ref([]);
const selectedVideo = ref({});
// const selectedPlaylist = ref("");
const requestGetPlaylist = async () => {
    const response = await axios.get('api-video/playlist');
    // console.log(response);
    videoPlaylist.value = response.data;
    requestGetVideo(videoPlaylist.value[0]);
}
requestGetPlaylist();

const requestGetVideo = async (event) => {
    const categoryName = ref("");
    if (!event.target) {
        categoryName.value = event.categoryName;
    } else categoryName.value = event.target.value;
    const response = await axios.get(`api-video/${categoryName.value}/videos`);
    videoInPlaylist.value = response.data;
    selectedVideo.value = videoInPlaylist.value[0];
}

</script>

<style scoped>
.content-wrap {
    display: flex;
    flex-direction: column;

    >h2 {
        align-self: flex-start;
    }

    >select {
        align-self: flex-end;
        border: 1px solid #ccc;
        border-radius: 16px;
        padding: 4px 12px;
        font-weight: 700;
    }

    >.content-box {
        display: flex;

        >.con-title {
            flex-grow: 3;
            flex-direction: column;

            img {
                cursor: pointer;
                margin: 1vh 0;
            }
        }

        >.detail {
            flex-grow: 7;
        }

    }
}
</style>