terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
    }
  }
}

resource "google_container_cluster" "primary" {
  name     = "manans-cluster"
  location = "us-east1-b"
  project = "stoked-axe-388413"
  remove_default_node_pool = true
  initial_node_count  = 1

}
resource "google_container_node_pool" "primary_preemptible_nodes" {
  name       = "node-pool"
  location   = "us-east1-b"
  cluster    = google_container_cluster.primary.name
  node_count = 1
  project = "stoked-axe-388413"
  node_config {
    preemptible     = false
    machine_type    = "e2-medium"
    disk_size_gb    = 10
    disk_type       = "pd-standard"
    image_type      = "COS_CONTAINERD"
    service_account = "terraform@stoked-axe-388413.iam.gserviceaccount.com"
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}