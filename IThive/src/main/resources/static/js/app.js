const showAddBlog = () => {
  const modal = document.querySelector(".add-blog");
  modal.classList.toggle("hidden");
};
const closeModal = () => {
  const modal = document.querySelector(".add-blog");
  modal.classList.toggle("hidden");
};

const closeEditModal = () => {
  document.querySelector(".edit-blog-container").style.display = "none";
  genericOpenModal(".edit-blog-container");
};
const showEditBlog = (id, title, content) => {
  document.querySelector(".edit-blog-container").style.display = "flex";

  const modal = document.querySelector(".edit-blog-container");
  const titleInput = modal.querySelector("#blog-title");
  const contentTextarea = modal.querySelector("textarea[name='content']");
  const blogIdInput = modal.querySelector("input[name='blog-id']");

  titleInput.value = title;
  contentTextarea.value = content;
  blogIdInput.value = Number(id);

  genericOpenModal(".edit-blog-container");
  console.log("prikaz podataka", id, " ", title, content);
};

let isModalOpen = false; // Track the modal state

const genericOpenModal = (className) => {
  const modal = document.querySelector(className);
  if (isModalOpen) {
    modal.style.display = "flex"; // Hide the modal
  } else {
    modal.style.display = "none"; // Show the modal
  }

  // Toggle modal state
  isModalOpen = !isModalOpen;
};

const showSortModal = (e) => {
  e.preventDefault();
  genericOpenModal(".button-group");
};
