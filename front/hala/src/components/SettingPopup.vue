<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Настройки шрифта</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-select
              v-model="selectedFont"
              :items="fonts"
              label="Выберите шрифт"
              required
            ></v-select>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" @click="closeDialog">Отмена</v-btn>
      <v-btn color="blue darken-1" @click="applyFont">Применить</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts">
import { ref, watch } from 'vue'

export default {
  props: {
    dialog: Boolean
  },
  emits: ['update:dialog'],
  setup(props, { emit }) {
    const internalDialog = ref(props.dialog)
    const selectedFont = ref('')
    const fonts = ref(['Arial', 'Verdana', 'Courier New'])

    watch(
      () => props.dialog,
      (newVal) => {
        internalDialog.value = newVal
      }
    )

    const closeDialog = () => {
      emit('update:dialog', false)
    }

    const applyFont = () => {
      localStorage.setItem('fontFamily', selectedFont.value)
      document.documentElement.style.setProperty('--font-family', selectedFont.value)
      closeDialog()
    }

    return {
      internalDialog,
      selectedFont,
      fonts,
      closeDialog,
      applyFont
    }
  }
}
</script>
