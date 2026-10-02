terraform {
    required_providers {
        google = {
            source = "hashicorp/google"
            version = "~> 5.0"
        }
    }
}

provider "google" {
    project = "battleforge-508609"
    region = "europe-west3"
}

resource "google_container_cluster" "battleforge" {
    name = "battleforge-cluster"
    location = "europe-west3-a"

    remove_default_node_pool = true
    initial_node_count = 1 
}

resource "google_container_node_pool" "battleforge_nodes" {
    name = "battleforge-node-pool"
    cluster = google_container_cluster.battleforge.name
    location = "europe-west3-a"

    node_count = 2

    node_config {
        machine_type = "e2-medium"
        disk_size_gb = 20
    }  
}