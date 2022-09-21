SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='ghcr.io/neptunus-platform/demo-band-service-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='default')
OUTPUT_TO_NULL_COMMAND = os.getenv("OUTPUT_TO_NULL_COMMAND", default=' > /dev/null ')

k8s_custom_deploy(
    'demo-band-service',
    apply_cmd="tanzu apps workload apply -f config/workload.yml --debug --live-update" +
               " --local-path " + LOCAL_PATH +
               " --source-image " + SOURCE_IMAGE +
               " --namespace " + NAMESPACE +
               " --yes " +
               OUTPUT_TO_NULL_COMMAND +
               " && kubectl get workload demo-band-service --namespace " + NAMESPACE + " -o yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yml --namespace " + NAMESPACE + " --yes",
    deps = ['build.gradle', './bin/main'],
    container_selector='workload',
    live_update = [
        sync('./bin/main', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('demo-band-service', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'serving.knative.dev/service': 'demo-band-service'}])

allow_k8s_contexts('do-fra1-neptunus-iterate-cluster')

update_settings(k8s_upsert_timeout_secs=60)
