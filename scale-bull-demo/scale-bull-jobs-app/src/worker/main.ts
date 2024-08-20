import { NestFactory } from "@nestjs/core";
import { FileDataModule } from "src/file-read/file-data.module";

async function main() {
    const app = await NestFactory.createApplicationContext(FileDataModule, 
        {
            bufferLogs: true,
            abortOnError: false,
        }
    );
    app.enableShutdownHooks();
    await app.init();
    console.log(`Worker started`);

    process.on('SIGINT', async () => {
        console.log(`SIGINT signal received`);
        try {
            console.log('closing app...');
            await app.close();
            console.log(`Worker stopped`);
        } catch (error) {
            console.error(`Error during shutdown: ${error.message}`);

        } finally {
            console.log('exiting...');
            process.exit(0);
        }
    });
}

main();