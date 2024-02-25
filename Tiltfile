SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='ghcr.io/thomasvitale/band-service-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='default')

k8s_custom_deploy(
    'band-service',
    apply_cmd="carto apps workload apply -f config/workload.yml --update-strategy replace --debug --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes --output yaml",
    delete_cmd="carto apps workload delete -f config/workload.yml --namespace " + NAMESPACE + " --yes",
    deps = ['build.gradle', './bin/main'],
    container_selector='workload',
    live_update = [
        sync('./bin/main', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('band-service', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'carto.run/workload-name': 'band-service', 'app.kubernetes.io/component': 'run'}])

allow_k8s_contexts('do-ams3-kadras-demo-cluster')
