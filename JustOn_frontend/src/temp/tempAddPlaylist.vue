<template>
    <div class="modal-overlay">
        <div class="modal-con" v-click-outside="closeAddPlaylistView">
            <h2>재생목록 추가하기</h2>
            <div>
                <form @submit.prevent="addPlaylist">
                    <div>
                        <label>목록명</label>
                        <input type="text" v-model="playlist.categoryName"/>
                    </div>
                    <div>
                        <label>설명</label>
                        <textarea v-model="playlist.groupDesc"></textarea>
                    </div>
                    <button>만들기</button>
                    <button type="button" @click="closeAddPlaylistView">취소</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref} from 'vue';
    import axios from 'axios';

    const playlist = ref({
        categoryName : "",
        groupDesc : "",
    });
    const emit = defineEmits(['closeAddPlaylistView', 'successAdd']);
    const closeAddPlaylistView = () => {
        emit('closeAddPlaylistView');
    }
    const addPlaylist = async () => {
        try {
            if(playlist.value.categoryName.trim() === "") {
                alert("재생목록명을 입력해주세요");
                return false;
            }
            const {response} = await axios.post("http://localhost:8080/api/video/playlist", playlist.value);
            emit('successAdd');
        } catch (error) {
            
        } finally {
            playlist.value.categoryName = "";
            playlist.value.groupDesc = "";
        }
    }
</script>

<style scoped>
    .modal-overlay{
        position: fixed;
        left: 0; top: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.5);
    }
    .modal-con{
        background-color: #fff;
    }
</style>