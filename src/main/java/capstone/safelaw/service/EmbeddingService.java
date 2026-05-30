package capstone.safelaw.service;

import ai.djl.huggingface.tokenizers.Encoding;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmbeddingService {

    private HuggingFaceTokenizer tokenizer;
    private OrtEnvironment env;
    private OrtSession session;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("⏳ AI 뇌(ONNX) 및 사전(Tokenizer) 가동 준비 중...");
        tokenizer = HuggingFaceTokenizer.newInstance(Paths.get("src/main/resources/tokenizer.json"));
        env = OrtEnvironment.getEnvironment();
        session = env.createSession("src/main/resources/model.onnx", new OrtSession.SessionOptions());
        System.out.println("🧠 AI 뇌 세팅 완벽하게 끝났습니다!");
    }

    public float[] getEmbedding(String text) throws Exception {
        // 1. 글자를 숫자로 쪼개기 (여기서 3가지 재료를 모두 뽑아냅니다!)
        Encoding encoding = tokenizer.encode(text);
        long[] inputIds = encoding.getIds();
        long[] attentionMask = encoding.getAttentionMask();
        long[] tokenTypeIds = encoding.getTypeIds(); // 👈 AI가 달라고 떼쓰던 3번째 재료 추가!

        // 2. ONNX가 먹을 수 있는 형태(2차원 배열)로 포장하기
        long[][] inputIdsArray = {inputIds};
        long[][] attentionMaskArray = {attentionMask};
        long[][] tokenTypeIdsArray = {tokenTypeIds};

        // 3. AI 뇌에 3가지 재료를 모두 집어넣고 결과물 뽑아내기
        try (OnnxTensor inputIdsTensor = OnnxTensor.createTensor(env, inputIdsArray);
             OnnxTensor attentionMaskTensor = OnnxTensor.createTensor(env, attentionMaskArray);
             OnnxTensor tokenTypeIdsTensor = OnnxTensor.createTensor(env, tokenTypeIdsArray)) {

            Map<String, OnnxTensor> inputs = new HashMap<>();
            inputs.put("input_ids", inputIdsTensor);
            inputs.put("attention_mask", attentionMaskTensor);
            inputs.put("token_type_ids", tokenTypeIdsTensor); // 👈 3번째 재료 투입!

            try (OrtSession.Result results = session.run(inputs)) {
                // 뽑아낸 1536개의 숫자 배열 리턴
                float[][][] output = (float[][][]) results.get(0).getValue();
                return output[0][0];
            }
        }
    }
}